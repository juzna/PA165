<#include "/layout/head.ftl">
<#include "/layout/navigation.ftl">
<div class="container">
	<div class="row-fluid">
		<#include "/account/_side.ftl">
		<div class="span8">
			<section id="account-">
				<header><h2>Access Forbidden</h2></header>
				<div class="alert alert-error">Access to this resource/action is forbidden.</div>
				<p>Please <a class="btn btn-primary" href="${loginUrl}">Login with Google</a></p>
			</section>
		</div>
	</div>
</div>
<#include "/layout/debug.ftl">
<#include "/layout/footer.ftl">
<#include "/layout/foot.ftl">