@extends('public.main')
@section('title', 'Sign Up Now')
@section('content')
    <h1>Sign up to OTG-Stor</h1>
    <div class="main-info form">
        <div id="sign-up">
            <form action="#" method="post">
                <div class="field-wrap">
                    <label>Name<span class="req">*</span> </label>
                    <input type="text" value="{{old('name')}}" name="name" required autocomplete="off"/>
                </div>
                <div class="field-wrap">
                    <label>Email<span class="req">*</span> </label>
                    <input type="email" value="{{old('email')}}" name="email" required autocomplete="off"/>
                </div>
                <div class="field-wrap">
                    <label>Phone<span class="req">*</span> </label>
                    <input type="text" name="phone" value="{{old('phone')}}" required autocomplete="off"/>
                </div>
                <div class="field-wrap">
                    <label>Password<span class="req">*</span> </label>
                    <input type="password" name="password" required autocomplete="off"/>
                </div>
                <div class="field-wrap">
                    <label>Confirm Password<span class="req">*</span> </label>
                    <input type="password" name="confirmation" required autocomplete="off"/>
                </div>
                {{ csrf_field() }}
                <button class="button button-block"/>Sign Up</button>
                <p class="sign">Have an Account?<a href="{{url('signin')}}"> Log In</a></p>
            </form>
        </div>
    </div>
@endsection
