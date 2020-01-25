module.exports = {
  up: (queryInterface, Sequelize) => {
    return queryInterface.addColumn(
        'Users',
        'isOnline',
        {
          type: Sequelize.BOOLEAN,
          allowNull: true
        }
      )
  },

  down: (queryInterface, Sequelize) => {
    return queryInterface.removeColumn(
        'Users',
        'isOnline'
      )
  }
};