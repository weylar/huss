import db from '../src/models';
import Sequelize from 'sequelize';

String.prototype.capitalize = function() {
  return this.charAt(0).toUpperCase() + this.slice(1);
};

class subCategoryService {
  static async createSubCategory(req) {
    const categoryId = req.params.categoryId;
    const category = await db.Category.findOne({ where: { id: categoryId } });

    if (!category) {
      return {
        status: 'error',
        statusCode: 404,
        message: 'No category with such id'
      };
    }

    req.body.categoryId = categoryId;
    const isExist = await db.SubCategory.findOne({ where: { name: req.body.name } });

    if(isExist) {
      return {
        status: 'error',
        statusCode: 409,
        message: 'Sub category already exists'
      }
    }
    const subCategory = await db.SubCategory.create(req.body);

    return {
      status: 'success',
      statusCode: 200,
      data: { category, subCategory },
      message: 'A new subCategory has been added'
    };
  }

  static async getSubCategory(req) {
    const category = await db.Category.findOne({ where: { id: req.params.categoryId } });
    const foundSubCategory = await db.SubCategory.findOne({ where: { id: req.params.id } });

    if (foundSubCategory.categoryId === category.id) {
      return {
        status: 'success',
        statusCode: 200,
        data: {foundSubCategory, category},
        message: 'Sub category sucessfully retrieved'
      }
    }

    return {
      status: 'error',
      statusCode: 404,
      message: 'Such sub category does not exist'
    }
  }
}

export default subCategoryService;
