import CategoryService from '../services/category';

class CategoryController {
  static async createCategory(req, res, next) {
    try {
      const response = await CategoryService.createCategory(req);
      
      return res.status(response.statusCode).json(response);
    } catch (e) {
      return next(e);
    }
  }

  static async getACategory(req, res, next) {
    try {
      const response = await CategoryService.getACategory(req);
      return res.status(response.statusCode).json(response);
    } catch (e) {
      return next(e);
    }
  }

  static async getAllCategories(req, res, next) {
    try {
      const response = await CategoryService.getAllCategories(req);
      return res.status(response.statusCode).json(response);
    } catch (e) {
      return next(e);
    }
  }
}

export default CategoryController;