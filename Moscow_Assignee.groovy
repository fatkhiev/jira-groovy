import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.link.IssueLink
import com.atlassian.jira.issue.link.IssueLinkTypeManager
import com.atlassian.jira.util.ImportUtils
import org.apache.log4j.Category
import com.atlassian.core.bean.EntityObject
import com.atlassian.jira.user.util.UserManager 


issueManager = ComponentAccessor.getIssueManager()
customFieldManager = ComponentAccessor.getCustomFieldManager()
userManager = ComponentAccessor.getUserManager()
EventDispatchOptionImpl eventDispatchOption = new EventDispatchOptionImpl()
techUser = userManager.getUser('noreply') 



//Minsk Assigne
CT = customFieldManager.getCustomFieldObjects(issue).find {it.name == 'City'}
City = issue.getCustomFieldValue(CT).toString()

if(City=="Moscow") {
    user=userManager.getUser('user1')  
    issue.setAssignee(user)
    issueManager.updateIssue(techUser,issue,eventDispatchOption.ISSUE_UPDATED,true)		
}


def wasIndexing = ImportUtils.indexIssues
ImportUtils.indexIssues = true