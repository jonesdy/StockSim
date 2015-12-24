var GetQuote = function (conf) {
   var defaults = {
      getQuoteUrl: ""
   };

   var $submitBtn = $("#submitBtn");
   var $symbol = $("#symbol");
   var $resultDiv = $("#resultDiv");
   var $symbolResult = $("#symbolResult");
   var $nameResult = $("#nameResult");
   var $costResult = $("#costResult");
   var $errorDiv = $("#errorDiv");
   var $errorText = $("#errorText");

   var config = $.extend(defaults, conf);

   var toDollars = function (value) {
      return "$" + value.toFixed(2).replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1,");
   }

   var init = function () {
      $symbol.keydown(function (key) {
         $resultDiv.hide();
         $errorDiv.hide();
         if (key.keyCode == 13) {
            $submitBtn.click();
            $symbol.blur();
         }
      });

      $submitBtn.on("click", function () {
         $.ajax({
            url: config.getQuoteUrl + $symbol.val(),
            success: function (data) {
               $errorDiv.hide();
               $symbolResult.text(data.Symbol);
               $nameResult.text(data.Name);
               $costResult.text(toDollars(data.Cost));
               $resultDiv.show();
            },
            error: function (xhr, options, error) {
               $errorText.text($symbol.val() + " is not a valid stock symbol!");
               $errorDiv.show();
            }
         });
      });
   };

   return {
      init: init
   };
};