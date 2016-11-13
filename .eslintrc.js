module.exports = {
  root: true,
  parser: 'babel-eslint',
  env: {
    "browser": true,
    "node": true
  },
  parserOptions: {
    sourceType: 'module'
  },
  // https://github.com/feross/standard/blob/master/RULES.md#javascript-standard-style
  extends: 'standard',
  // required to lint *.vue files
  plugins: [
    'html'
  ],
  // add your custom rules here
  'rules': {
    // allow paren-less arrow functions
    'arrow-parens': 0,
    // allow async-await
    'generator-star-spacing': 0,
    // allow func()
    'space-before-function-paren': 0,
    // allow blank line before }
    'padded-blocks': 0,
    // allow semicolon
    'semi': 0,
    // allow unused defined var
    'no-unused-vars': 0,
    // allow indent
    'indent': 0,
    // allow quotes
    'quotes': 0,
    // allow same line properties
    'object-property-newline': 0,

    'operator-linebreak': 0,

    'keyword-spacing': 0,

    'no-trailing-spaces': 0,
    // allow debugger during development
    'no-debugger': process.env.NODE_ENV === 'production' ? 2 : 0
  }
}
