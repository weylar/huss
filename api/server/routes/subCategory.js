import { Router } from 'express';
import subCategoryController from '../controllers/subCategory';
import { subCategoryCheck } from '../middleware/validations/subCategory';
import Auth from '../middleware/utils/Auth';

const { createSubCategory, getSubCategory, getAllSubCategories, getAllSubCategoriesByLimit,
paginateSubCategories, getSubCategoriesSuggest, editSubCategory,deleteSubCategory } = subCategoryController;
const { getUser, adminCheck } = Auth;

const subCategoryRouter = Router();

subCategoryRouter.post('/:categoryId/create', getUser, adminCheck, subCategoryCheck, createSubCategory);
subCategoryRouter.get('/getASubCategory/:id', getUser, getSubCategory);
subCategoryRouter.get('/:categoryId/getAllSubCategories', getUser, getAllSubCategories);
subCategoryRouter.get('/:categoryId/getAllSubCategoriesByLimit/:limit', getUser, getAllSubCategoriesByLimit);
subCategoryRouter.get('/:categoryId/paginateSubCategories/:offset/:limit', getUser, paginateSubCategories);
subCategoryRouter.get('/:categoryId/getSubCategoriesSuggest/:offset/:name/:limit', getUser, getSubCategoriesSuggest);
subCategoryRouter.put('/editSubCategory/:id', getUser, editSubCategory);
subCategoryRouter.delete('/:categoryId/deleteSubCategory/:id', getUser, deleteSubCategory);

export default subCategoryRouter;