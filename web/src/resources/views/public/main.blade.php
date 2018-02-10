<DOCTYPE html>
<html>
    <head>
        <meta carset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>OTG - @yield('title')</title>


        <link   href =   "{{asset("resources/assets/public/css/bootstrap.css")}}" rel="stylesheet">
        <link   href =   "{{asset('resources/assets/public/css/font-awesome.min.css')}}" rel="stylesheet">
        <link   href =   "{{asset('resources/assets/public/css/log-in.css')}}" rel="stylesheet">
        <script src  =   "{{asset('resources/resources/assets/public/js/jquery-2.2.3.min.js')}}"></script>

        <link href='https://fonts.googleapis.com/css?family=Philosopher' rel='stylesheet' type='text/css'>
        <link href='https://fonts.googleapis.com/css?family=Raleway' rel='stylesheet' type='text/css'>

    </head>
    <body>

    <div class="main">
        <div class="title center" style="text-align: center;">
            <a href="{{url('/')}}">Home</a>
        </div>
        @yield('content')
    </div>
    <!-- //main -->

    <!-- copyright -->
    <div class="footer">
        <p>© 2017 designed by<a href="store.html" target="_blank"> OTG-Stor Team</a></p>
    </div>
    <!-- //copyright -->






    <script>
        jQuery('.form').find('input, textarea').on('keyup blur focus', function (e) {
            var $this = $(this),
                label = $this.prev('label');

            if (e.type === 'keyup') {
                if ($this.val() === '') {
                    label.removeClass('active highlight');
                } else {
                    label.addClass('active highlight');
                }
            } else if (e.type === 'blur') {
                if( $this.val() === '' ) {
                    label.removeClass('active highlight');
                } else {
                    label.removeClass('highlight');
                }
            } else if (e.type === 'focus') {

                if( $this.val() === '' ) {
                    label.removeClass('highlight');
                }
                else if( $this.val() !== '' ) {
                    label.addClass('highlight');
                }
            }

        });
    </script>

    </body>
</html>