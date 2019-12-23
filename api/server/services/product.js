import db from '../src/models';
import Sequelize from 'sequelize';

String.prototype.capitalize = function() {
  return this.charAt(0).toUpperCase() + this.slice(1);
}

class AdService {
  static async createAd(req) {
    const categoryId = req.params.categoryId;
    const subCategoryId = req.params.subCategoryId;

    const category = await db.Category.findOne({ where: { id: categoryId } });
    const subCategory = await db.SubCategory.findOne({ where: { id: subCategoryId } });

    if (!category) {
      return {
        status: 'error',
        statusCode: 404,
        message: 'No category with such parameter'
      };
    }

    if (!subCategory) {
      return {
        status: 'error',
        statusCode: 404,
        message: 'No sub category with such parameter'
      };
    }

    req.body.categoryId = categoryId;
    req.body.subCategoryId = subCategoryId;
    req.body.userId = req.userId;

    const ad = await db.Product.create(req.body);

    return {
      status: 'success',
      statusCode: 200,
      data: { category, subCategory, ad },
      message: 'A new ad has been added'
    };
  }

  static async getAd(req) {
    const editViewCount = await db.Product.update({ count: count + 1, where: { id: req.params.id }});

    if (editViewCount[0] === 1) {
      
      const foundAd = await db.Product.findOne({ where : { id: req.params.id } })
      const foundSubCategory = await db.SubCategory.findOne({ where: { id: foundAd.subCategoryId } });
      const category = await db.Category.findOne({ where: { id: foundAd.categoryId } });

      return {
        status: 'success',
        statusCode: 200,
        data: {foundAd, foundSubCategory, category},
        message: 'Ad sucessfully retrieved'
      }
    }

    return {
      status: 'error',
      statusCode: 404,
      message: 'Such ad does not exist'
    }
  }
}

export default AdService;