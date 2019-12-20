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

  static async getAllCategoriesByLimit(req, res, next) {
    try {
      const response = await CategoryService.getAllCategoriesByLimit(req);
      return res.status(response.statusCode).json(response);
    } catch (e) {
      return next(e);
    }
  }

  static async paginateCategories(req, res, next) {
    try {
      const response = await CategoryService.paginateCategories(req);
      return res.status(response.statusCode).json(response);
    } catch (e) {
      return next(e);
    }
  }

  static async getCategoriesSuggest(req, res, next) {
    try {
      const response = await CategoryService.getCategoriesSuggest(req);
      return res.status(response.statusCode).json(response);
    } catch (e) {
      return next(e);
    }
  }

  static async editCategory(req, res, next) {
    try {
      const response = await CategoryService.editCategory(req);
      return res.status(response.statusCode).json(response);
    } catch (e) {
      return next(e);
    }
  }

  static async deleteCategory(req, res, next) {
    try {
      const response = await CategoryService.deleteCategory(req);
      return res.status(response.statusCode).json(response);
    } catch (e) {
      return next(e);
    }
  }
}

export default CategoryController;