'use strict';
module.exports = (sequelize, DataTypes) => {
  const Category = sequelize.define('Category', {
    name: {
      type: DataTypes.STRING,
      allowNull: false
    },
    categoryImageUrl: {
      type: DataTypes.STRING,
      allowNull: false
    },
    belongedAd: {
      type: DataTypes.INTEGER,
      allowNull: true
    }
  }, {});
  Category.associate = function(models) {
    Category.hasMany(models.Product,{
      foreignKey: 'categoryName',
      onUpdate: 'CASCADE',
      onDelete: 'CASCADE'
    });
    Category.hasMany(models.SubCategory,{
      foreignKey: 'categoryId',
      onUpdate: 'CASCADE',
      onDelete: 'CASCADE'
    });
  };
  return Category;
};