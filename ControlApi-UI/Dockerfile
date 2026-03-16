# Etapa 1: Build
FROM node:20-alpine AS build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build -- --configuration production

# Etapa 2: Servidor
FROM nginx:alpine
# Copia o build do Angular (ajustado para a estrutura do Angular 17+)
COPY --from=build /app/dist/control-api-ui/browser /usr/share/nginx/html
# Copia a configuração personalizada do Nginx
COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
