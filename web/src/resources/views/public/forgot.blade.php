@extends('public.main')
@section('title', 'Forgot Password')
@section('content')
<h1>Reset your password</h1>
<div class="main-info form">
    <div id="login">
        <form action="{{url('forgot')}}" method="post">
            <div class="field-wrap">
                <label> Enter Your Email<span class="req">*</span> </label>
                <input type="email"required autocomplete="off"/>
            </div>
            <!-- DOTO :: Human detector
            -->
            <button class="button button-block"/><a href="log-in.html">Continue</a></button>
        </form>
    </div>
</div>
@endsection