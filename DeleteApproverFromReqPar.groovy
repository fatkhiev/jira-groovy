import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.util.ImportUtils
import org.apache.log4j.Category
import com.atlassian.core.bean.EntityObject
import com.atlassian.jira.user.util.UserManager
import com.atlassian.jira.issue.util.DefaultIssueChangeHolder
import com.atlassian.jira.issue.customfields.option.*
import com.atlassian.jira.issue.fields.*
import com.atlassian.jira.issue.ModifiedValue
import com.atlassian.crowd.embedded.api.User
import com.atlassian.jira.event.type.EventDispatchOption.EventDispatchOptionImpl

issueManager = ComponentAccessor.getIssueManager()
customFieldManager = ComponentAccessor.getCustomFieldManager()
userManager = ComponentAccessor.getUserManager()
EventDispatchOptionImpl eventDispatchOption = new EventDispatchOptionImpl()
techUser = userManager.getUser('noreply')

def i=0
RP = customFieldManager.getCustomFieldObjects(issue).find {it.name == 'Request participants'}
ReqPar = issue.getCustomFieldValue(RP)
app = customFieldManager.getCustomFieldObjects(issue).find {it.name == 'Approver'}
approver = issue.getCustomFieldValue(app)

DefaultIssueChangeHolder issueChangeHolder = new DefaultIssueChangeHolder()

if (issue.getSummary().toLowerCase().contains('Развертывание виртуальной'.toLowerCase())){
ArrayList<User> users = new ArrayList<User>()

for (part in ReqPar) {
	for (appobj in approver){
    	if(part.name.contains(appobj.name)) {
    	user = userManager.getUserByName(appobj.name)	
    	users.add(user)
    	}
	}
}
NewReqPar=ReqPar.minus(users)
RP.updateValue(null, issue, new ModifiedValue(ReqPar,NewReqPar), issueChangeHolder);
issueManager.updateIssue(techUser,issue,eventDispatchOption.ISSUE_UPDATED,true)
}

