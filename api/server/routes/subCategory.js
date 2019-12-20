import { Router } from 'express';
import subCategoryController from '../controllers/subCategory';
import { subCategoryCheck } from '../middleware/validations/subCategory';
import Auth from '../middleware/utils/Auth';
import app from '../..';

const { createSubCategory } = subCategoryController;
const { getUser, adminCheck } = Auth;

const subCategoryRouter = Router();

subCategoryRouter.post('/:categoryId/create', getUser, adminCheck, subCategoryCheck, createSubCategory);

export default subCategoryRouter;