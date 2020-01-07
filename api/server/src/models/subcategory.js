'use strict';
module.exports = (sequelize, DataTypes) => {
  const SubCategory = sequelize.define('SubCategory', {
    categoryId: {
      type: DataTypes.INTEGER,
      allowNull: true
    },
    categoryName: {
      type: DataTypes.STRING,
      allowNull: true
    },
    name: {
      type: DataTypes.STRING,
      allowNull: false
    }
  }, {});
  SubCategory.associate = function(models) {
    // associations can be defined here
    SubCategory.hasMany(models.Product, {
      foreignKey: 'subCategoryId',
      onUpdate: 'CASCADE',
      onDelete: 'CASCADE'
    })
  };
  return SubCategory;
};