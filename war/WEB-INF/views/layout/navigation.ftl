<div class="navbar">
	<div class="navbar-inner">
		<div class="container">
			<div class="userarea" style="float: right;">
				<#if user??>
					<p>You are logged in as ${user.getEmail()} <a class="btn btn-mini" href="${logoutUrl}">Logout</a></p>
				<#else>
					<p><a class="btn btn-primary" href="${loginUrl}">Login with Google</a></p>
				</#if>
			</div>
			<a class="brand" href="/">jCardsManager</a>
			<div class="nav-collapse collapse">
				<ul class="nav">
					<li class="active"><a href="/">Homepage</a></li>
					<li class=""><a href="/browse/">Browse</a></li>
					<li class=""><a href="/account/">Account</a></li>
				</ul>
			</div>
		</div>
	</div>
</div>