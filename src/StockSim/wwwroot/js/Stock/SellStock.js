var SellStock = function (conf) {
   var defaults = {
      getQuoteUrl: ""
   };

   var $getQuoteBtn = $("#getQuoteBtn");
   var $tickerSymbol = $("#tickerSymbol");
   var $quoteDiv = $("#quoteDiv");
   var $quoteCost = $("#quoteCost");
   var $errorDiv = $("#errorDiv");
   var $errorText = $("#errorText");

   var config = $.extend(defaults, conf);

   var toDollars = function (value) {
      return "$" + value.toFixed(2).replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1,");
   }

   var init = function () {
      $tickerSymbol.keydown(function (key) {
         $quoteDiv.hide();
         $errorDiv.hide();
      });

      $getQuoteBtn.on("click", function () {
         $.ajax({
            url: config.getQuoteUrl + $tickerSymbol.val(),
            success: function (data) {
               $errorDiv.hide();
               $quoteCost.text(toDollars(data.Cost));
               $quoteDiv.show();
            },
            error: function (xhr, options, error) {
               $errorText.text($tickerSymbol.val() + " is not a valid stock symbol!");
               $errorDiv.show();
            }
         });
      });
   };

   return {
      init: init
   };
};