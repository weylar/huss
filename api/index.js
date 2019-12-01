import express from 'express';
import bodyParser from 'body-parser';
import cors from 'cors';
import userRoutes from './server/routes/user';

const app = express();

app.use(cors());
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

const port = process.env.PORT || 3000;
// when a random route is inputed
app.get('/', (req, res) => res.status(200).send({
  status: 'success',
  statusCode: 200,
  message: 'Welcome to the huss API.'
}));

app.use('/api/v1/auth', userRoutes)

app.use((req, res, next) => {
  const error = new Error('Route Does not Exist');
  error.status = 404;
  next(error);
});

app.use((error, req, res, next) => {
  res.status(error.status || 500);
  res.send({
    status: 'error',
    statuscode: error.status || 500,
    error: error.name,
    message: error.message,
  });
  next();
});

app.listen(port, () => {
  console.log(`Server is running on PORT ${port}`);
});

export default app;