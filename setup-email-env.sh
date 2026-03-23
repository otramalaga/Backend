#!/bin/bash

# Script para configurar variables de entorno de email
# Uso: ./setup-email-env.sh

echo "🔧 Configuración de Variables de Entorno para Email"
echo "=================================================="
echo ""

# Verificar si existe .env
if [ ! -f .env ]; then
    echo "📝 Creando archivo .env desde env.example..."
    cp env.example .env
    echo "✅ Archivo .env creado"
    echo ""
fi

echo "Por favor, ingresa la siguiente información:"
echo ""

# Solicitar configuración de email
read -p "📧 Email (MAIL_USERNAME): " mail_username
read -sp "🔐 App Password o contraseña (MAIL_PASSWORD): " mail_password
echo ""
read -p "🌐 URL del Frontend [http://localhost:5173]: " frontend_url
frontend_url=${frontend_url:-http://localhost:5173}

# Actualizar .env
echo ""
echo "📝 Actualizando archivo .env..."

# Actualizar MAIL_USERNAME
if grep -q "^MAIL_USERNAME=" .env; then
    sed -i "s|^MAIL_USERNAME=.*|MAIL_USERNAME=$mail_username|" .env
else
    echo "MAIL_USERNAME=$mail_username" >> .env
fi

# Actualizar MAIL_PASSWORD
if grep -q "^MAIL_PASSWORD=" .env; then
    sed -i "s|^MAIL_PASSWORD=.*|MAIL_PASSWORD=$mail_password|" .env
else
    echo "MAIL_PASSWORD=$mail_password" >> .env
fi

# Actualizar FRONTEND_URL
if grep -q "^FRONTEND_URL=" .env; then
    sed -i "s|^FRONTEND_URL=.*|FRONTEND_URL=$frontend_url|" .env
else
    echo "FRONTEND_URL=$frontend_url" >> .env
fi

echo "✅ Variables actualizadas en .env"
echo ""
echo "📋 Resumen de configuración:"
echo "   MAIL_HOST: smtp.gmail.com (por defecto)"
echo "   MAIL_PORT: 587 (por defecto)"
echo "   MAIL_USERNAME: $mail_username"
echo "   FRONTEND_URL: $frontend_url"
echo ""
echo "⚠️  IMPORTANTE:"
echo "   - Para Gmail, necesitas usar una 'App Password', no tu contraseña normal"
echo "   - Activa la verificación en 2 pasos en tu cuenta de Google"
echo "   - Genera una App Password en: https://myaccount.google.com/apppasswords"
echo ""
echo "🚀 Para probar, ejecuta:"
echo "   mvn spring-boot:run"
echo ""





