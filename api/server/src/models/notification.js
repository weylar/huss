'use strict';
module.exports = (sequelize, DataTypes) => {
  const Notification = sequelize.define('Notification', {
    userId: {
      type: DataTypes.INTEGER,
      allowNull: true
    },
    message: {
      type: DataTypes.STRING,
      allowNull: false
    }
  }, {});
  Notification.associate = function(models) {
    // associations can be defined here
    Notification.belongsTo(model.User, {
      foreignKey: 'id',
      onUpdate: 'CASCADE',
      onDelete: 'CASCADE'
    })
  };
  return Notification;
};