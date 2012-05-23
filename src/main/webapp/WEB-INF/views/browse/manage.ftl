<#include "/layout/head.ftl">
<#include "/layout/navigation.ftl">
<div class="container">
	<div class="row-fluid">
		<div class="span4">
			<section id="browse-groups">
				<header><h2>User area</h2></header>
				<ul class="nav nav-tabs nav-stacked">
					<li><a href="#">Overview</a></li>
					<li><a href="#">Manage</a></li>
					<li><a href="#">Upload</a></li>
				</ul>
			</section>
		</div>

		<div class="span8">
			<section id="account-panel">
				<header><h2>Manage your cards</h2></header>
				<table class="table table-bordered table-condensed table-striped">
					{foreach $cards as $card}
					<tr><td width="90"><img src="http://placekitten.com/90/50" /></td><td><a href="#">{$card['name']}</a></td><td>{ifset $card['private']}PUBLIC{/ifset}</td></tr>
					{/foreach}
				</table>
			</section>
		</div>
	</div>
</div>
<#include "/layout/debug.ftl">
<#include "/layout/footer.ftl">
<#include "/layout/foot.ftl">