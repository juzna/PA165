<#include "/layout/head.ftl">
<#include "/layout/navigation.ftl">
<div class="container">
	<div class="row-fluid">

		<div class="span4">
			<section id="browse-groups">
				<header><h2>My groups <a href="/account/groups/" class="btn btn-mini">Manage groups</a></h2></header>
				<#if user??>
					<#if groups??>
						<ul class="nav nav-tabs nav-stacked">
							<#list groups as group>
								<li class="group <#if group == activeGroup!"null">active</#if>" data-id="${group.key.id?c}">
									<a href="/browse/?group=${group.key.id?c}">${group.name}</a>
								</li>
							</#list>
						</ul>
					<#else>
						<p class="well">No groups</p>
					</#if>
				<#else>
					<p class="well">Auth area</p>
				</#if>
			</section>
				
			<section id="browse-recent">
				<header><h2>Recent cards</h2></header>
				<#if recentPublicCards??>
					<ul>
						<#list recentPublicCards as card>
							<li><a href="/browse/card/${card.key.id?c}/">${card.name}</a></li>
						</#list>
					</ul>
				<#else>
					<p class="well">No data</p>
				</#if>
			</section>
		</div>


		<div class="span8">
			<section id="browse-browser">
				<header><h2>Browsing all mine and public cards</h2></header>
				<#if (cards?size > 0)>
					<#list cards as card>
						<div class="card row-fluid">
							<div class="span6">
								<div class="card-image">
									<img src="/cardImage/${card.img.getKeyString()}" />
								</div>
								<div class="card-meta">
									<p>By ${card.owner} at ${card.created?date}</p>
								</div>
							</div>
							<div class="span6">
								<h4 class="card-name"><a href="/browse/card/${card.key.id?c}/">${card.name!}</a></h4>
								<#if card.tags??>
									<dl class="card-tags">
										<#list card.tags as tag>
											<dt>${tag.tagKey!"Undefined"}</dt><dd>${tag.content!"Undefined"}</dd>
										</#list>
									</dl>
								<#else>
									<p class="well">No data</p>
								</#if>
							</div>
						</div>
					</#list>
				<#else>
					<p class="well">No cards</p>
				</#if>
				<div class="pagination">
					<ul>
						<#if page <= 1>
							<li class="disabled"><a>«</a></li>
						<#else>
							<li><a href="/browse/?page=${page - 1}">«</a></li>
						</#if>
						<li><a href="/browse/?page=${page + 1}">»</a></li>
					</ul>
				</div>
			</section>
		</div>

	</div>
</div>
<#include "/layout/debug.ftl">
<#include "/layout/footer.ftl">
<#include "/layout/foot.ftl">