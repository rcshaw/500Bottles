/**
 *
 */
(function(){
  /* Parent of image gallery jQuery selector. */
  var GALLERY_PARENT_SEL = ".wine_grid_container";

  /* jQuery selector for gallery container element. */
  var GALLERY_CONTAINER_SEL = ".wine_grid";

  /* Prefix to add to gallery element class names. */
  var GALLERY_CLASS_PREFIX = "wine_";

  /* jQuery selector for elements within GALLERY_CONTAINER to treat as image. */
  var IMAGE_ELEMENT_SEL = ".wine";

  /* jQuery selector for next page button. */
  var NEXT_PAGE_BTN = ".next";

  /* jQuery selector for previous page button. */
  var PREV_PAGE_BTN = ".prev";

  /* Number of image gallery rows to create. Can be overwritten by setting
   * data-row attribute of the GALLERY_CONTAINER_SEL.
   */
  var NUM_OF_ROWS = 2;

  var SLIDE_MARGIN = 100;

  /************************** End Gallery Config ******************************/

  /* Holds references to image objects. */
  var images = [];        // populated by guild_gallery

  /* Holds references to each gallery row. */
  var image_rows = [];    // populated by build_gallery()

  /* Max number of images per row. */
  var images_per_row;     // calculated by build_gallery()

  /* Binds button event handlers. */
  function bind_events(gallery_parent_selector) {

      $(gallery_parent_selector).each(function(index, el) {

          $(el).children(NEXT_PAGE_BTN).on("click", function() {
              return (function(e) {
                  next_page(e);
              })(el);
          } );

          $(el).children(PREV_PAGE_BTN).on("click", function() {
              return (function(e) {
                  previous_page(e);
              })(el);
          });

      });

      $(gallery_parent_selector + " " + IMAGE_ELEMENT_SEL).hover(function() {
          $(this).addClass("flipped");
      }, function() {
          $(this).removeClass("flipped");
      });
  }

  /* Builds internal representation of gallery. */
  function build_gallery(gallery_parent_selector) {
    var i, j,               // loop index
        start_index,        // start index for adding images to rows
        end_index;     // count of images per row

    // Remove transitions.
    $(gallery_parent_selector + " " + GALLERY_CONTAINER_SEL).addClass("no_transition");

    //NUM_OF_ROWS = $(GALLERY_PARENT_SEL).data("rows") || NUM_OF_ROWS;
    NUM_OF_ROWS = $(gallery_parent_selector).data("rows") || NUM_OF_ROWS;

    // get all the images
    images = $(gallery_parent_selector + " " + GALLERY_CONTAINER_SEL).children(IMAGE_ELEMENT_SEL);

    // calculate the number of images for each row
    images_per_row = Math.ceil(images.length / NUM_OF_ROWS);

    // build row containers
    for (i = 0; i < NUM_OF_ROWS; i++) {
      image_rows[i] = $("<div/>").addClass(GALLERY_CLASS_PREFIX + "row");

      // calculate the start and end indexes for this row
      start_index = i * images_per_row;
      end_index = (i + 1) * images_per_row;

      // add the images to this row
      for (j = start_index; j < end_index; j++) {

        // stop the loop if we've run out of images
        if (j >= images.length) break;

        // append the image to the row container
        $(image_rows[i]).append(images[j]);
      }

      // add this row container to the gallery container
      $(gallery_parent_selector + " " + GALLERY_CONTAINER_SEL).append(image_rows[i]);
    }

    // position and size the gallery
    position_gallery(gallery_parent_selector + " " + GALLERY_CONTAINER_SEL);

    // Replace transitions.
      setTimeout(function() {
          $(gallery_parent_selector + " " + GALLERY_CONTAINER_SEL).removeClass("no_transition");
      }, 500);



    bind_events(gallery_parent_selector);
  }

  /* Positions images within the gallery container. */
  function position_gallery(selector) {
    var image_width,
        image_height,
        image_selector;

    image_selector = "." + GALLERY_CLASS_PREFIX + "row " + IMAGE_ELEMENT_SEL;

    // TODO: why do I have to add 5px here when the number of images increases?
    image_width = $(image_selector).outerWidth(true);
    image_height = $(image_selector).outerHeight(true);

    $(selector).css({
      "width": image_width * images_per_row,
      "height": image_height * NUM_OF_ROWS
    });
  }

  /* Scrolls gallery to next page. */
  function next_page(gallery_parent) {
    var gallery_container = $(gallery_parent).children(GALLERY_CONTAINER_SEL);

    var parent_width = $(gallery_parent).width();
    var gallery_width = $(gallery_container).width();
    var gallery_position = parseInt($(gallery_container).css("margin-left"));
    var new_position = gallery_position - parent_width + SLIDE_MARGIN;

    if (-new_position > gallery_width - parent_width)
      new_position = -(gallery_width - parent_width);

    $(gallery_container).css("margin-left", new_position + "px");
  }

  /* Scrolls gallery to previous page. */
  function previous_page(gallery_parent) {
      var gallery_container = $(gallery_parent).children(GALLERY_CONTAINER_SEL);

    var parent_width = $(gallery_parent).width();
    var gallery_width = $(gallery_container).width();
    var gallery_position = parseInt($(gallery_container).css("margin-left"));
    var new_position = gallery_position + parent_width - SLIDE_MARGIN;

    if (new_position > 0)
      new_position = 0;

    $(gallery_container).css("margin-left", new_position + "px");
  }

  /* Handles window resize events. */
  function on_window_resize() {
    var parent_width = $(GALLERY_PARENT_SEL).width();
    var gallery_position = parseInt($(GALLERY_CONTAINER_SEL).css("margin-left"));
    var gallery_width = $(GALLERY_CONTAINER_SEL).width();

    if (-gallery_position > gallery_width - parent_width)
      gallery_position = -(gallery_width - parent_width);

    $(GALLERY_CONTAINER_SEL).css("margin-left", gallery_position + "px");
  }

    /* Fire Away! */
    //build_gallery();
    //bind_events();
    $(window).on("resize", on_window_resize);

    window._500bottles = window._500bottles || {};
    window._500bottles.gallery = window._500bottles.gallery || {};
    window._500bottles.gallery.buildGallery = window._500bottles.gallery.buildGallery || build_gallery;

})();
