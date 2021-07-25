'use strict';

const path                          = require('path');
const merge                         = require('webpack-merge');
const commonConfig                  = require('./webpack.common.js');
const { CleanWebpackPlugin }        = require('clean-webpack-plugin');

module.exports = merge(commonConfig, {
    mode: 'production',
    plugins: [
        new CleanWebpackPlugin(),
    ],
    output: {
        filename: 'main.js',
        path: path.resolve(__dirname, 'src', 'main', 'resources', 'static', 'js')
    }
});