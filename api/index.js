import express from 'express';
import bodyParser from 'body-parser';
import cors from 'cors';
import { userRouter, userDetails } from './server/routes/user';
import categoryRouter from './server/routes/category';
import subCategoryRouter from './server/routes/subCategory';
import adRouter from './server/routes/product';
import adImageRouter from './server/routes/image';
import favoriteRouter from './server/routes/favorite';
import notificationRouter from './server/routes/notification';
import adReportRouter from './server/routes/adReport';

const app = express();

app.use(cors());
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

const port = process.env.PORT || 3000;

app.get('/', (_,res) => res.status(200).send({
  status: 'success',
  statusCode: 200,
  message: 'Welcome to the huss API.'
}));

app.use('/api/v1/auth', userRouter);
app.use('/api/v1/user', userDetails);
app.use('/api/v1/category', categoryRouter);
app.use('/api/v1/subCategory', subCategoryRouter);
app.use('/api/v1/ad', adRouter);
app.use('/api/v1/adImage', adImageRouter);
app.use('/api/v1/favorite', favoriteRouter);
app.use('/api/v1/notification', notificationRouter);
app.use('/api/v1/adReport', adReportRouter);

app.use((req, res, next) => {
  const error = new Error('Route Does not Exist');
  error.status = 404;
  next(error);
});

app.use((error, req, res, next) => {
  res.status(error.status || 500);
  res.send({
    status: 'error',
    statusCode: error.status || 500,
    error: error.name,
    message: error.message,
  });
  next();
});

app.listen(port, () => {
  console.log(`Server is running on PORT ${port}`);
});

export default app;