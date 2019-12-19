import db from '../src/models';
import Sequelize from 'sequelize';

class CategoryService {
  static async createCategory(req) {
      const category = await db.Category.create(req.body);

      return {
        status: 'success',
        statusCode: 200,
        data: category,
        message: 'A new category has been added'
      }
  }

  static async getACategory(req) {
    const foundCategory = await db.Category.findOne({ where: { id: req.params.id } });

    if (foundCategory) {
      return {
        status: 'success',
        statusCode: 200,
        data: foundCategory,
        message: 'Category sucessfully retrieved'
      }
    }

    return {
      status: 'error',
      statusCode: 404,
      message: 'Such category does not exist'
    }
  }

  static async getAllCategories() {
    const foundCategories = await db.Category.findAll({ order: [ ['name', 'ASC'] ]});

    if (foundCategories) {
      return {
        status: 'success',
        statusCode: 200,
        data: foundCategories,
        message: 'Categories have been sucessfully retrieved'
      }
    }

    return {
      status: 'error',
      statusCode: 404,
      message: 'No category exists'
    }
  }
}

export default CategoryService;