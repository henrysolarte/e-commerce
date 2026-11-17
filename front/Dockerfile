# Frontend - Clemenss Perfumería
# Sirve archivos estáticos con un servidor web simple

FROM node:18-alpine

WORKDIR /app

# Copiar archivos del frontend
COPY . .

# Instalar serve para servir archivos estáticos
RUN npm install -g serve

# Exponer puerto (Render proporcionará la variable de entorno PORT)
EXPOSE 8080

# Comando para iniciar el servidor
# serve sirve archivos estáticos en el puerto especificado
CMD ["sh", "-c", "serve -s . -l ${PORT:-8080}"]
