<#include "/layout/head.ftl">
<#include "/layout/navigation.ftl">
<div class="container">
	<div class="row-fluid">
		<#include "/account/_side.ftl">
		<div class="span8">
			<section id="account-cards">
				<header><h2>Cards overview</h2></header>
				<#if cards??>
					<div class="row-fluid">
						<#list cards as card>
							<div class="card card_thumb span4" data-id="${card.key.id?c}">
								<div class="card-image"><img src="/cardImage/${card.img.getKeyString()}" /></div>
								<div class="card-meta">
									<p class="report"><a href="/account/card/${card.key.id?c}/">Edit</a></p>
									<p>${card.created?date}</p>
								</div>
								<h4 class="card-name"><a href="/browse/card/${card.key.id?c}/">${card.name}</a></h4>
							</div>
						</#list>
					</ul>
				<#else>
					<p class="well">No data</p>
				</#if>
			</section>
			</section>
		</div>
	</div>
</div>
<#include "/layout/debug.ftl">
<#include "/layout/footer.ftl">
<#include "/layout/foot.ftl">