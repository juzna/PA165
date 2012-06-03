<#include "/layout/head.ftl">
<#include "/layout/navigation.ftl">
<div class="container">
	<div class="row-fluid">
		<#include "/account/_side.ftl">
		<div class="span8">
			<section id="account-overview">
				<header><h2>User overview</h2></header>
				<table class="table table-bordered table-striped">
					<colgroup><col class="span1"><col class="span7"></colgroup>
					<tbody>
						<tr><td>Nickname:</td><td>${user.nickname}</td></tr>
						<tr><td>E-mail:</td><td>${user.email}</td></tr>
						<tr><td>ID:</td><td><small>${user.userId}</small></td></tr>
					</tbody>
				</table>
				<p><a class="btn btn-danger">Log out</a></p>
			</section>
		</div>
	</div>
</div>
<#include "/layout/debug.ftl">
<#include "/layout/footer.ftl">
<#include "/layout/foot.ftl">