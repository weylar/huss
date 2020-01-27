import { Router } from 'express';
import CategoryController from '../controllers/category';
import { categoryCheck } from '../middleware/validations/category';
import Auth from '../middleware/utils/Auth';

const { createCategory, getACategory, getAllCategories, getAllCategoriesByLimit, paginateCategories,
getCategoriesSuggest, editCategory, deleteCategory, getPopularCategories
 } = CategoryController;
const { getUser, adminCheck } = Auth;

const categoryRouter = Router();

categoryRouter.post('/create', getUser, adminCheck, categoryCheck, createCategory);
categoryRouter.get('/:id', getUser, getACategory);
categoryRouter.get('/all/categories', getAllCategories);
categoryRouter.get('/getAllCategoriesByLimit/:limit', getAllCategoriesByLimit);
categoryRouter.get('/all/getPopularCategories', getPopularCategories);
categoryRouter.get('/paginateCategories/:offset/:limit', getUser, paginateCategories);
categoryRouter.get('/getCategoriesSuggest/:offset/:name/:limit', getUser, getCategoriesSuggest);
categoryRouter.put('/editCategory/:id', getUser, editCategory);
categoryRouter.delete('/deleteCategory/:id', getUser, deleteCategory);

export default categoryRouter;