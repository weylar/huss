import { Router } from 'express';
import CategoryController from '../controllers/category';
import { categoryCheck } from '../middleware/validations/category';
import Auth from '../middleware/utils/Auth';

const { createCategory, getACategory, getAllCategories } = CategoryController;
const { getUser, adminCheck } = Auth;

const categoryRouter = Router();

categoryRouter.post('/create', getUser, adminCheck, categoryCheck, createCategory);
categoryRouter.get('/:id', getUser, getACategory);
categoryRouter.get('/all/categories', getUser, getAllCategories)

export default categoryRouter;