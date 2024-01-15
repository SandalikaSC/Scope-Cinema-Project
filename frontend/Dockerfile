
FROM node
WORKDIR /frontend

ENV PATH /frontend/node_modules/.bin:$PATH
COPY . /frontend

RUN npm i

CMD ["npm", "run", "dev"]