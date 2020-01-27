'use strict';
module.exports = (sequelize, DataTypes) => {
  const AdReport = sequelize.define('AdReport', {
    productId: {
      type: DataTypes.INTEGER,
      allowNull: true
    },
    reporterId: {
      type: DataTypes.INTEGER,
      allowNull: false
    },
    reason: {
      type: DataTypes.STRING,
      allowNull: false
    },
    userId: {
      type: DataTypes.INTEGER,
      allowNull: true
    }
  }, {});
  AdReport.associate = function(models) {
    // associations can be defined here
    AdReport.belongsTo(models.User, {
      foreignKey: 'id',
      onUpdate: 'CASCADE',
      onDelete: 'CASCADE'
    });
  };
  return AdReport;
};