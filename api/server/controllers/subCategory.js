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
}

export default subCategoryController;