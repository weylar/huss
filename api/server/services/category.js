import db from '../src/models';
import Sequelize from 'sequelize';

String.prototype.capitalize = function() {
  return this.charAt(0).toUpperCase() + this.slice(1);
}

class CategoryService {
  static async createCategory(req) {
    const isExist = await db.Category.findOne({ where: { name: req.body.name } });
    if(isExist) {
      return {
        status: 'error',
        statusCode: 409,
        message: 'This category exists already'
      }
    }
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

    if (foundCategories.length > 1) {
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

  static async getAllCategoriesByLimit(req) {
    const limit = req.params.limit;
    const allCategories = await db.Category.findAll({ limit,  order: [ ['name', 'ASC'] ] });

    if(allCategories.length > 1) {
      return {
        status: 'success',
        statusCode: 200,
        data: allCategories,
        message: 'All categories retrieved successfully'
      }
    }
    return {
      status:'error',
      statusCode: 404,
      message: 'No such category'
    }
  }

  static async paginateCategories(req) {
    const limit = req.params.limit;
    const offset = req.params.offset;
    const allCategories = await db.Category.findAll({ offset, limit, order: [ ['name', 'ASC'] ] });

    if(allCategories.length > 1) {
      return {
        status: 'success',
        statusCode: 200,
        data: allCategories,
        message: 'All categories retrieved successfully'
      }
    }
    return {
      status:'error',
      statusCode: 404,
      message: 'No such category'
    }
  }

  static async getCategoriesSuggest(req) {
    const limit = req.params.limit;
    const offset = req.params.offset;
    let name = req.params.name;
    name = name.capitalize()
    const Op = Sequelize.Op;
    const allCategories = await db.Category.findAll({ offset, limit, where: { name: { [Op.startsWith]: `%${name}%` } } });

    if(allCategories.length > 1) {
      return {
        status: 'success',
        statusCode: 200,
        data: allCategories,
        message: 'All categories retrieved successfully'
      }
    }
    return {
      status:'error',
      statusCode: 404,
      message: 'No such category'
    }

  }

  static async editCategory(req) {
    const id = req.params.id;

    const foundUser = await db.User.findOne({ where: { id: req.userId, isAdmin: true } });

    if (!foundUser) {
      return {
        status: 'error',
        statusCode: 401,
        message: 'This action can only be performed by an admin'
      }
    }

    if(!req.body.categoryImageUrl) {
      return {
        status: 'error',
        statusCode: 422,
        message: `Please select an image`,
      }
    }

    const editedCategory = await db.Category.update({ categoryImageUrl: req.body.categoryImageUrl }, {where: {id: id} });
    
    if(editedCategory[0] === 0) {
      return {
        status: 'error',
        statusCode: 404,
        message: 'Such category doesn\'t exist'
      }
    }

    const newCategory = await db.Category.findOne({ where: { id: id }});

    return {
      status: 'success',
      statusCode: 202,
      data: newCategory,
      message: 'This category has been successfully edited'
    }
  }

  static async deleteCategory(req) {
    const id = req.params.id;

    const foundUser = await db.User.findOne({ where: { id: req.userId, isAdmin: true } });

    if (foundUser) {
      const deletedCategory = await db.Category.destroy({ where: {id: id} });

      if(!deletedCategory) {
        return {
          status: 'error',
          statusCode: 404,
          message: 'Such category doesn\'t exist'
        }
      }
  
      const newCategories = await db.Category.findAll();
  
      return {
        status: 'success',
        statusCode: 200,
        data: newCategories,
        message: 'This category has been successfully deleted'
      }
    }
    return {
      status: 'error',
      statusCode: 401,
      message: 'This action can only be performed by an admin'
    }

  }
}

export default CategoryService;