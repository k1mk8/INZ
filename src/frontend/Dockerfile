FROM node:18-alpine
WORKDIR .
COPY package*.json ./
RUN npm install
RUN npm install semantic-ui-angular-jquery
RUN npm i -g semantic-ui --save
COPY . .
EXPOSE 3000
CMD ["ng", "serve"]