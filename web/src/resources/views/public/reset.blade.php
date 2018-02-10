@extends('public.main')
@section('title', 'Reset Password')
@section('content')
    <h1>Reset your password</h1>
    <div class="main-info form">
        <div id="login">
            <form action="{{url('forgot')}}" method="post">
                <div class="field-wrap">
                    <label> Enter New Password<span class="req">*</span> </label>
                    <input type="password" name="password" required autocomplete="off"/>
                </div>

                <div class="field-wrap">
                    <label> Confirm The New Password<span class="req">*</span> </label>
                    <input type="password"  name="password_confirm" required autocomplete="off"/>
                </div>
                {{ csrf_field() }}
                <button class="button button-block"/><a href="log-in.html">Continue</a></button>
            </form>
        </div>
    </div>
@endsection