FROM node:16.17.1-alpine3.16 as build

WORKDIR /app

COPY package-lock.json /app/
COPY package.json /app/

RUN npm i
RUN npm install -g serve

COPY src /app/src
COPY public /app/public
COPY tsconfig.json /app/

RUN npm run build

CMD [ "serve", "-s", "build" ]