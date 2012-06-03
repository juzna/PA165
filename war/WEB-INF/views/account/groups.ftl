<#include "/layout/head.ftl">
<#include "/layout/navigation.ftl">
<div class="container">
	<div class="row-fluid">
		<#include "/account/_side.ftl">
		<div class="span8">
			<section id="account-">
				<header><h2>Groups overview</h2></header>
				<#if groups??>
					<#list groups as group>
						<div class="group-item" data-id="${group.key.id}">
							<form method="POST" class="form-horizontal">
								<input type="hidden" name="form-groups-id" value="${group.key.id}" />
								<input type="text" name="form-groups-name" id="form-groups-name" value="${group.name}">
								<button type="submit" class="btn" name="do" value="edit">Save</button>
								<button type="submit" class="btn btn-danger" name="do" value="delete">Delete</button>
							</form>
						</div>
					</#list>
				<#else>
					<p class="well">No groups</p>
				</#if>
			</section>
		</div>
	</div>
</div>
<#include "/layout/debug.ftl">
<#include "/layout/footer.ftl">
<#include "/layout/foot.ftl">