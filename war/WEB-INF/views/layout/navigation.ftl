<div class="navbar">
	<div class="navbar-inner">
		<div class="container">
			<span class="userarea" style="float: right;">
				<#if user??>
					<p>You are logged in as ${user.getEmail()} <a class="btn btn-mini" href="${logoutUrl}">Logout</a></p>
				<#else>
					<a href="${loginUrl}">Login with Google</button>
				</#if>
			</span>
			<a class="brand" href="/">jCardsManager</a>
			<div class="nav-collapse collapse">
				<ul class="nav">
					<li class="active"><a href="/">Homepage</a></li>
					<li class=""><a href="/browse/">B Browse</a></li>
					<li class=""><a href="/account/upload/">AUpload</a></li>
					<li class=""><a href="/account/manage/">A Manage</a></li>
				</ul>
			</div>
		</div>
	</div>
</div>