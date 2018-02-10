@extends('public.main')
@section('title', 'Sign Up Now')
@section('content')
    <h1>Sign up to OTG-Stor</h1>
    <div class="main-info form">
        <div id="sign-up">
            <form action="#" method="post">
                <div class="field-wrap">
                    <label>Name<span class="req">*</span> </label>
                    <input type="name"required autocomplete="off"/>
                </div>
                <div class="field-wrap">
                    <label>Email<span class="req">*</span> </label>
                    <input type="email"required autocomplete="off"/>
                </div>
                <div class="field-wrap">
                    <label>Phone<span class="req">*</span> </label>
                    <input type="text"required autocomplete="off"/>
                </div>
                <div class="field-wrap">
                    <label>Password<span class="req">*</span> </label>
                    <input type="password"required autocomplete="off"/>
                </div>
                <div class="field-wrap">
                    <label>Confirm Password<span class="req">*</span> </label>
                    <input type="password"required autocomplete="off"/>
                </div>
                {{ csrf_field() }}
                <button class="button button-block"/>Sign Up</button>
                <p class="sign">Have an Account?<a href="{{url('signin')}}"> Log In</a></p>
            </form>
        </div>
    </div>
@endsection
