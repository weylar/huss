import subCategoryService from '../services/subCategory';

class subCategoryController {
  static async createSubCategory(req, res, next) {
    try {
      const response = await subCategoryService.createSubCategory(req);
      
      return res.status(response.statusCode).json(response);
    } catch (e) {
      return next(e);
    }
  }

  static async getSubCategory(req, res, next) {
    try {
      const response = await subCategoryService.getSubCategory(req);
      
      return res.status(response.statusCode).json(response);
    } catch (e) {
      return next(e);
    }
  }

  static async getAllSubCategories(req, res, next) {
    try {
      const response = await subCategoryService.getAllSubCategories(req);
      
      return res.status(response.statusCode).json(response);
    } catch (e) {
      return next(e);
    }
  }

  static async getAllSubCategoriesByLimit(req, res, next) {
    try {
      const response = await subCategoryService.getAllSubCategoriesByLimit(req);
      
      return res.status(response.statusCode).json(response);
    } catch (e) {
      return next(e);
    }
  }

  static async paginateSubCategories(req, res, next) {
    try {
      const response = await subCategoryService.paginateSubCategories(req);
      
      return res.status(response.statusCode).json(response);
    } catch (e) {
      return next(e);
    }
  }

  static async getSubCategoriesSuggest(req, res, next) {
    try {
      const response = await subCategoryService.getSubCategoriesSuggest(req);
      
      return res.status(response.statusCode).json(response);
    } catch (e) {
      return next(e);
    }
  }

  static async editSubCategory(req, res, next) {
    try {
      const response = await subCategoryService.editSubCategory(req);
      
      return res.status(response.statusCode).json(response);
    } catch (e) {
      return next(e);
    }
  }
}

export default subCategoryController;