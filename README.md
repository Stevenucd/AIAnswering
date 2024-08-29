# AIAnswering
## Using AI to answer questions

> Author：[Steven Cui](https://github.com/Stevenucd)

## Project Profile

### Project Description

**AI question answering app platform** based on Vue 3 + Spring Boot + Redis + ChatGLM AI.

Users can quickly create and publish a variety of applications based on AI, and support retrieve, answering questions online, and obtaining answer summaries based on scoring algorithms or AI; administrators can review applications, centrally manage the entire site's content.

### Project Launch

- Setup project by running `npm install` in terminal

- Connect to your own MySQL database (Follow **_MySQL Database_** under **_Functions_** in `AIAnswering-backend/README.md`)

- Debug `AIAnswering-backend/src/main/java/com.steven.AIAnswering/MainApplication`

- Debug **_serve_** in `aianswering-frontend/package.json`

- Visit `http://localhost:8080/`

- Login with the default admin Account: `steven` Password: `12345678` which is the initial data created at step 1.

### Project Showcase

Homepage:

![image](https://github.com/Stevenucd/AIAnswering/blob/main/AIAnswering-backend/doc/homepage.png)

Application details page:

![image](https://github.com/Stevenucd/AIAnswering/blob/main/AIAnswering-backend/doc/appDetails.png)

User answer page:

![image](https://github.com/Stevenucd/AIAnswering/blob/main/AIAnswering-backend/doc/userAnswer.png)

Create an application page:

![image](https://github.com/Stevenucd/AIAnswering/blob/main/AIAnswering-backend/doc/createApp.png)

Create a question page:

![image](https://github.com/Stevenucd/AIAnswering/blob/main/AIAnswering-backend/doc/createQuestion.png)

Application management page:

![image](https://github.com/Stevenucd/AIAnswering/blob/main/AIAnswering-backend/doc/appManagement.png)

User management page:

![image](https://github.com/Stevenucd/AIAnswering/blob/main/AIAnswering-backend/doc/userManagement.png)

_**etc...**_

## Technology selection

### Backend

- Java Spring Boot Framework (See `AIAnswering-backend/README.md`)
- Storage Layer: MySQL Database + Redis Cache + Tencent Cloud COS Object Storage
- MyBatis-Plus and MyBatis X automatic generation
- Generic AI capabilities based on the ChatGLM big model
- Multiple design patterns

### Frontend

- Vue 3
- Vue-CLI Scaffold
- Axios Request Library
- Arco Design Component Library
- Frontend engineering: ESLint + Prettier + TypeScript
- Pinia Status Management
- OpenAPI Frontend Code Generation

### Development Tools
- Backend IDE：JetBrains IDEA
- Frontend IDE：JetBrains WebStorm