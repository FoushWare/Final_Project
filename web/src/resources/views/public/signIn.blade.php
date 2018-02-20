@extends('public.main')
@section('title', 'Sign In Now')
@section('content')
    <h1>Login to OTG-Store</h1>
    <div class="main-info form">
        <div id="login">
            <form action="{{url('signin')}}" method="post">
                <div class="field-wrap">
                    <label> Enter Your Email<span class="req">*</span> </label>
                    <input type="email" name="email" value="{{old('email')}}" required autocomplete="off"/>
                </div>
                <div class="field-wrap">
                    <label> Your Password<span class="req">*</span> </label>
                    <input type="password" name="password" required autocomplete="off"/>
                </div>
                <p class="forgot"><a href="{{url('forgot')}}">Forgot Password?</a></p>
                {{ csrf_field() }}
                <button class="button button-block"/>Log In</button>
                <p class="sign">Log in with your<a href="{{url('QRLogin')}}"> QR code</a>.</p>
                <p class="sign">New customer?<a href="{{url('signup')}}"> Sign up now !</a></p>
            </form>
        </div>
    </div>
@endsection
