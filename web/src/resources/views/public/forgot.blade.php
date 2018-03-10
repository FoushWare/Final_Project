@extends('public.main')
@section('title', 'Forgot Password')
@section('content')
<h1>Reset your password</h1>
<div class="main-info form">
    <div id="login">
        <form action="{{url('forgot')}}" method="post">
            <div class="field-wrap">
                <label> Enter Your Email<span class="req">*</span> </label>
                <input type="email" name="email" required/>
            </div>
            <!-- DOTO :: Human detector
            -->
            {{ csrf_field() }}
            <input class="button button-block" type="submit" value="Continue"/>
            <p class="sign">logIn From <a href="{{url('signin')}}">Here</a>.</p>
            <p class="sign">New customer?<a href="{{url('signup')}}"> Sign up now !</a></p>
        </form>
    </div>
</div>
@endsection