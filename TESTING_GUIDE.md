# Guía de Pruebas - Verificación de Email y Recuperación de Contraseña

## 📋 Configuración de Variables de Entorno

### 1. Configurar Email (Gmail como ejemplo)

#### Opción A: Usar archivo .env (Recomendado para desarrollo local)

1. **Crea un archivo `.env` en la raíz del proyecto BackEndProd:**
```bash
cd BackEndProd
cp env.example .env
```

2. **Edita el archivo `.env` con tus credenciales:**
```env
# Configuración de Email
MAIL_HOST=smtp.gmail.com
MAIL_PORT=587
MAIL_USERNAME=tu-email@gmail.com
MAIL_PASSWORD=tu-app-password-de-gmail
FRONTEND_URL=http://localhost:5173
```

#### Opción B: Exportar variables en la terminal

```bash
export MAIL_HOST=smtp.gmail.com
export MAIL_PORT=587
export MAIL_USERNAME=tu-email@gmail.com
export MAIL_PASSWORD=tu-app-password
export FRONTEND_URL=http://localhost:5173
```

### 2. Configurar Gmail para enviar emails

**IMPORTANTE:** Para usar Gmail, necesitas generar una "App Password":

1. Ve a tu cuenta de Google: https://myaccount.google.com/
2. Activa la **Verificación en 2 pasos** si no la tienes activada
3. Ve a **Seguridad** → **Contraseñas de aplicaciones**
4. Genera una nueva contraseña de aplicación para "Correo"
5. Copia la contraseña generada (16 caracteres sin espacios)
6. Usa esa contraseña en `MAIL_PASSWORD` (NO uses tu contraseña normal de Gmail)

**Alternativa:** Si no quieres usar Gmail, puedes usar otros servicios:
- **Mailtrap** (para desarrollo/testing): https://mailtrap.io/
- **SendGrid**: https://sendgrid.com/
- **Amazon SES**: https://aws.amazon.com/ses/

### 3. Configurar Mailtrap (Alternativa fácil para testing)

Si prefieres una solución más simple para pruebas:

1. Regístrate en https://mailtrap.io/ (gratis)
2. Crea una "Inbox" de prueba
3. Configura las variables:
```env
MAIL_HOST=smtp.mailtrap.io
MAIL_PORT=2525
MAIL_USERNAME=tu-username-de-mailtrap
MAIL_PASSWORD=tu-password-de-mailtrap
FRONTEND_URL=http://localhost:5173
```

---

## 🧪 Guía de Pruebas Paso a Paso

### Prueba 1: Registro y Verificación de Email

1. **Inicia el backend:**
```bash
cd BackEndProd
mvn spring-boot:run
```

2. **Inicia el frontend:**
```bash
cd FrontEnd
npm run dev
```

3. **Registra un nuevo usuario:**
   - Ve a http://localhost:5173/Register
   - Completa el formulario con un email real que puedas verificar
   - Haz clic en "Registrarse"
   - Deberías ver el mensaje: "¡Registro exitoso! Por favor verifica tu email..."

4. **Verifica el email:**
   - Revisa tu bandeja de entrada (o spam)
   - Deberías recibir un email con el asunto "Verifica tu email - Otra Málaga"
   - Haz clic en el enlace del email
   - Serás redirigido a la página de verificación
   - Deberías ver: "¡Email verificado exitosamente!"

5. **Intenta iniciar sesión:**
   - Ve a http://localhost:5173/Login
   - Intenta iniciar sesión con las credenciales del usuario registrado
   - **ANTES de verificar:** Deberías ver el error: "Por favor verifica tu email antes de iniciar sesión"
   - **DESPUÉS de verificar:** Deberías poder iniciar sesión correctamente

### Prueba 2: Recuperación de Contraseña

1. **Solicita recuperación:**
   - Ve a http://localhost:5173/Login
   - Haz clic en "¿Olvidaste tu contraseña?"
   - Ingresa el email de un usuario registrado
   - Haz clic en "Enviar enlace de recuperación"
   - Deberías ver: "Si el email existe, se ha enviado un enlace de recuperación..."

2. **Verifica el email de recuperación:**
   - Revisa tu bandeja de entrada
   - Deberías recibir un email con el asunto "Recuperación de contraseña - Otra Málaga"
   - Haz clic en el enlace del email
   - Serás redirigido a la página de restablecimiento

3. **Restablece la contraseña:**
   - Ingresa una nueva contraseña (debe cumplir los requisitos)
   - Confirma la contraseña
   - Haz clic en "Restablecer Contraseña"
   - Deberías ver: "Contraseña restablecida exitosamente"
   - Serás redirigido al login

4. **Inicia sesión con la nueva contraseña:**
   - Usa el email y la nueva contraseña
   - Deberías poder iniciar sesión correctamente

### Prueba 3: Casos de Error

1. **Token de verificación inválido:**
   - Intenta acceder a: http://localhost:5173/verify-email?token=token-invalido
   - Deberías ver un error

2. **Token de recuperación expirado:**
   - Genera un token de recuperación
   - Espera más de 1 hora (o modifica el código para reducir el tiempo)
   - Intenta usar el token
   - Deberías ver un error de token expirado

3. **Email no verificado intentando login:**
   - Registra un usuario pero NO verifiques el email
   - Intenta iniciar sesión
   - Deberías ver: "Por favor verifica tu email antes de iniciar sesión"

---

## 🔍 Verificación en Base de Datos

Puedes verificar directamente en la base de datos que los campos se están guardando:

```sql
-- Ver usuarios y su estado de verificación
SELECT id, name, email, email_verified, verification_token, 
       reset_password_token, reset_password_token_expiry
FROM users;

-- Verificar que un usuario tiene email verificado
SELECT * FROM users WHERE email = 'tu-email@ejemplo.com';
```

---

## 🐛 Solución de Problemas

### Error: "Could not connect to SMTP host"

**Causa:** Las variables de entorno no están configuradas correctamente.

**Solución:**
1. Verifica que las variables estén exportadas: `echo $MAIL_HOST`
2. Si usas `.env`, asegúrate de que tu aplicación lo esté cargando
3. Verifica que el puerto no esté bloqueado por firewall

### Error: "Authentication failed"

**Causa:** Credenciales incorrectas o App Password no configurada.

**Solución:**
1. Para Gmail, asegúrate de usar una App Password, no tu contraseña normal
2. Verifica que `MAIL_USERNAME` sea tu email completo
3. Verifica que `MAIL_PASSWORD` sea correcta

### No se reciben emails

**Causa:** Emails en spam o configuración incorrecta.

**Solución:**
1. Revisa la carpeta de spam
2. Verifica los logs del backend para ver errores
3. Usa Mailtrap para desarrollo (más confiable para testing)

### Error: "Token de verificación inválido"

**Causa:** El token en la URL no coincide con el guardado.

**Solución:**
1. Asegúrate de copiar el token completo de la URL
2. Verifica que `FRONTEND_URL` esté configurado correctamente
3. Los tokens son únicos y solo funcionan una vez

---

## 📝 Logs Útiles

Para ver los logs del backend y detectar problemas:

```bash
# En el backend, busca estos mensajes en la consola:
- "Email sent successfully"
- "Error sending email"
- "User not found"
- "Token expired"
```

---

## ✅ Checklist de Pruebas

- [ ] Variables de entorno configuradas
- [ ] Backend iniciado sin errores
- [ ] Frontend iniciado sin errores
- [ ] Registro de usuario funciona
- [ ] Email de verificación recibido
- [ ] Verificación de email funciona
- [ ] Login bloqueado sin verificación
- [ ] Login funciona después de verificación
- [ ] Solicitud de recuperación funciona
- [ ] Email de recuperación recibido
- [ ] Restablecimiento de contraseña funciona
- [ ] Login con nueva contraseña funciona

---

## 🚀 Próximos Pasos

Una vez que todo funcione:

1. **Producción:** Configura un servicio de email profesional (SendGrid, AWS SES, etc.)
2. **Seguridad:** Considera agregar rate limiting para prevenir spam
3. **UX:** Agrega un botón "Reenviar email de verificación" si el usuario no recibió el email
4. **Testing:** Crea tests automatizados para estas funcionalidades





