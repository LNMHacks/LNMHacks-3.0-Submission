/**
 * Medical Theme
 *
 * This file contains all template JS functions
 *
 * @package Medical
--------------------------------------------------------------
                   Contents
--------------------------------------------------------------
 * 01 - Owl Carousel
          - Home Slider    


--------------------------------------------------------------*/
(function($) {
  "use strict";
  
// Owl Carousel
  // Home Slider
    $("#home-slider").owlCarousel({
      loop: true,
      items: 1,
      dots: true,
      nav: true,
      autoplayTimeout: 6000,
      smartSpeed: 2000,
      autoHeight: false,
      touchDrag: true,
      mouseDrag: true,
      margin: 0,
      autoplay: true,
      slideSpeed: 600,
      navText: ['<i class="fa fa-angle-left" aria-hidden="true"></i>', '<i class="fa fa-angle-right" aria-hidden="true"></i>'],
      responsive: {
        0: {
            items: 1,
            nav: false,
            dots: true,
        },
        600: {
            items: 1,
            nav: false,
            dots: true,
        },
        768: {
            items: 1,
            nav: false,
            dots: true,
        },
        1100: {
            items: 1,
            nav: true,
            dots: true,
        }
      }
    });
// Template slider
     $("#testimonial-slider").owlCarousel({
      loop: true,
      items: 1,
      dots: true,
      nav: true,
      autoplayTimeout: 6000,
      smartSpeed: 2000,
      autoHeight: false,
      touchDrag: true,
      mouseDrag: true,
      margin: 0,
      autoplay: true,
      slideSpeed: 600,
      navText: ['<i class="fa fa-angle-left" aria-hidden="true"></i>', '<i class="fa fa-angle-right" aria-hidden="true"></i>'],
      responsive: {
        0: {
            items: 1,
            nav: false,
            dots: true,
        },
        600: {
            items: 1,
            nav: false,
            dots: true,
        },
        768: {
            items: 1,
            nav: false,
            dots: true,
        },
        1100: {
            items: 1,
            nav: true,
            dots: true,
        }
      }
    });

// Lightbox Gallery
    // Product Gallery
    $('.gallery-popup').magnificPopup({
      type: 'image',
      closeOnContentClick: true,
      mainClass: 'mfp-img-mobile',
      image: {
        verticalFit: true,
        titleSrc: function(item) {
          return item.el.attr('title') + '<small>by Medical Theme</small>';
        }
      }
    });


})(jQuery);