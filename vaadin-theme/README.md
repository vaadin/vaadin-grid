# Vaadin Valo Theme

A customizable Sass-based theme engine for Vaadin Components, which allows you
to generate new "skins" for any or all of the components.

## Install

First install [Node.js](http://nodejs.org), then run the following command:

    $ npm install -g vaadin-theme

If you receive an EACCES error, you need to
[fix npm permissions](https://docs.npmjs.com/getting-started/fixing-npm-permissions).

This installs the npm package and the corresponding 'vaadin-theme' command.

## Generating themes

Create a Sass file in your project, and use the Vaadin Valo theme API to
describe the new theme.

Set the `$v-theme-name` variable to if you want to scope the styles for and use
them in additional to some other theme on the same page.

Remember to import the "vaadin-theme" after you've modified the variables.

my-theme.scss:

    $v-theme-name: "my-theme";
    $v-included-components: button, grid;
    $v-included-additional-styles: none;

    $v-font-family: Helvetica Neue, sans-serif;
    $v-font-weight: 500;
    $v-border: 0;
    $v-border-radius: 0;
    $v-background-color: #00b4f0;
    $v-bevel: false;
    $v-shadow: false;
    $v-gradient: false;
    $v-unit-size: 40px;

    @import "vaadin-theme";

After that, compile the file using the `vaadin-theme` command:

    $ vaadin-theme my-theme.scss

The output is generated into a file named "custom-theme.css" (i.e. the same file
name as the input but a different file type), in the same folder as the input.

You can also specify an explicit output path as a second argument for the command:

    $ vaadin-theme custom-theme.scss path/to/output.css
    
and use flags `--debug` and `--watch`.

## Using themes

Include a CSS file generated using `vaadin-theme` in your HTML page as any other
CSS stylesheet:

    <link rel="stylesheet" href="path/to/custom-theme.css">

Specify the <code>theme</code> attribute for the components you wish to use the
custom theme. The attribute value should match the `$v-theme-name` Sass variable
value:

    <v-button theme="my-theme">Custom theme</v-button>
