'use strict';

module.exports = {
  up: (queryInterface, Sequelize) => {
    return Promise.all([
      queryInterface.addColumn(
        'Products',
        'payDate',
        {
          type: Sequelize.DATE,
          allowNull: true
        }
      ),
      queryInterface.addColumn(
        'Products',
        'location',
        {
          type: Sequelize.STRING,
          allowNull: true
        }
      )
    ])
    /*
      Add altering commands here.
      Return a promise to correctly handle asynchronicity.

      Example:
      return queryInterface.createTable('users', { id: Sequelize.INTEGER });
    */
  },

  down: (queryInterface, Sequelize) => {
    return Promise.all([
      queryInterface.removeColumn(
        'Products',
        'payDate'
      ),
      queryInterface.removeColumn(
        'Products',
        'location'
      )
    ]);
    /*
      Add reverting commands here.
      Return a promise to correctly handle asynchronicity.

      Example:
      return queryInterface.dropTable('users');
    */
  }
};
