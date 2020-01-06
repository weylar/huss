import db from '../src/models';
import Sequelize from 'sequelize';

String.prototype.capitalize = function() {
  return this.charAt(0).toUpperCase() + this.slice(1);
};

class subCategoryService {
  static async createSubCategory(req) {
    const categoryName = req.params.categoryName.capitalize();
    const category = await db.Category.findOne({ where: { name: categoryName } });

    if (!category) {
      return {
        status: 'error',
        statusCode: 404,
        message: 'No category with such name'
      };
    }

    req.body.categoryId = category.id;
    req.body.categoryName = categoryName;
    const isExist = await db.SubCategory.findOne({ where: { name: req.body.name } });

    if (isExist) {
      return {
        status: 'error',
        statusCode: 409,
        message: 'Sub category already exists'
      };
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
    const foundSubCategory = await db.SubCategory.findOne({ where: { id: req.params.id } });

    if (foundSubCategory) {
      return {
        status: 'success',
        statusCode: 200,
        data: foundSubCategory,
        message: 'Sub category sucessfully retrieved'
      };
    }

    return {
      status: 'error',
      statusCode: 404,
      message: 'Such sub category does not exist'
    };
  }

  static async getAllSubCategories(req) {
    const subCategories = await db.SubCategory.findAll({
      where: { categoryName: req.params.categoryName.capitalize() },
      order: [['name', 'ASC']]
    });

    if (subCategories) {
      return {
        status: 'success',
        statusCode: 200,
        data: subCategories,
        message: 'Sub categories have been sucessfully retrieved'
      };
    }
  }

  static async getAllSubCategoriesByLimit(req) {
    const allSubCategories = await db.SubCategory.findAll({
      where: { categoryName: req.params.categoryName.capitalize() },
      limit: req.params.limit,
      order: [['name', 'ASC']]
    });

    if (allSubCategories) {
      return {
        status: 'success',
        statusCode: 200,
        data: allSubCategories,
        message: 'All sub categories retrieved successfully'
      };
    }
  }

  static async paginateSubCategories(req) {
    const limit = req.params.limit;
    const offset = req.params.offset;
    const allSubCategories = await db.SubCategory.findAll({
      where: { categoryName: req.params.categoryName },
      offset,
      limit,
      order: [['name', 'ASC']]
    });

    if (allSubCategories) {
      return {
        status: 'success',
        statusCode: 200,
        data: allSubCategories,
        message: 'All categories retrieved successfully'
      };
    }
  }

  static async getSubCategoriesSuggest(req) {
    const limit = req.params.limit;
    const offset = req.params.offset;
    let name = req.params.name;
    name = name.capitalize();
    const Op = Sequelize.Op;
    const allSubCategories = await db.SubCategory.findAll({
      offset,
      limit,
      where: { categoryName: req.params.categoryName, name: { [Op.startsWith]: `%${name}%` } }
    });

    if (allSubCategories) {
      return {
        status: 'success',
        statusCode: 200,
        data: allSubCategories,
        message: 'All categories retrieved successfully'
      };
    }
  }

  static async editSubCategory(req) {
    const foundUser = await db.User.findOne({ where: { id: req.userId, isAdmin: true } });

    if (!foundUser) {
      return {
        status: 'error',
        statusCode: 401,
        message: 'This action can only be performed by an admin'
      };
    }

    if (!req.body.name) {
      return {
        status: 'error',
        statusCode: 422,
        message: `Please select an image`
      };
    }

    const editedCategory = await db.SubCategory.update(
      { name: req.body.name },
      { where: { id: req.params.id } }
    );

    if (editedCategory[0] === 0) {
      return {
        status: 'error',
        statusCode: 404,
        message: "Such sub category doesn't exist"
      };
    }

    const newSubCategory = await db.SubCategory.findOne({ where: { id: req.params.id } });

    return {
      status: 'success',
      statusCode: 202,
      data: newSubCategory,
      message: 'This sub category has been successfully edited'
    };
  }

  static async deleteSubCategory(req) {
    const foundUser = await db.User.findOne({ where: { id: req.userId, isAdmin: true } });

    if (foundUser) {
      const deletedSubCategory = await db.SubCategory.destroy({ where: { id: req.params.id } });

      if (!deletedSubCategory) {
        return {
          status: 'error',
          statusCode: 404,
          message: "Such category doesn't exist"
        };
      }

      return {
        status: 'success',
        statusCode: 200,
        data: deletedSubCategory,
        message: 'Successfully deleted'
      };
    }
    return {
      status: 'error',
      statusCode: 401,
      message: 'This action can only be performed by an admin'
    };
  }
}

export default subCategoryService;
