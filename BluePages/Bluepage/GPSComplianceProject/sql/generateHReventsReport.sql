truncate table ITIMAM.hr_events immediate;

-- 'New Hire', 'PE record outstanding', 'Access outstanding';
insert into ITIMAM.hr_events (select a.CNUM, a.FULL_NAME, a.EMAILID, a.DEPT, a.DIV, a.ISMANAGER, a.MGRCNUM, a.JOB_ROLE, a.SKILL_SET, current date, 0, 'New Hire', 'PE record outstanding', 'Access outstanding', substr(a.cnum, 7, 3) from ITIMAM.bp_current a where not exists (select b.emailid from ITIMAM.bp_previous b  where a.emailid=b.emailid) and not exists (select c.mail from itimuser.person_mail c where a.emailid=c.mail) and not exists (select d.dn from itimuser.account d, itimuser.account_owner e, itimuser.person_mail c where d.dn=e.dn and e.owner=c.dn and a.emailid=c.mail));

-- 'New Hire', 'PE record created', 'Access outstanding';
insert into ITIMAM.hr_events (select a.CNUM, a.FULL_NAME, a.EMAILID, a.DEPT, a.DIV, a.ISMANAGER, a.MGRCNUM, a.JOB_ROLE, a.SKILL_SET, current date, 0, 'New Hire', 'PE record created', 'Access outstanding', substr(a.cnum, 7, 3) from ITIMAM.bp_current a where not exists (select b.emailid from ITIMAM.bp_previous b where a.emailid=b.emailid) and exists (select c.mail from itimuser.person_mail c where a.emailid=c.mail) and not exists (select d.selfdn from itimuser.account d, itimuser.account_owner e, itimuser.person_mail c where d.dn=e.dn and e.owner=c.dn and a.emailid=c.mail));

-- 'Termination', 'PE records affected ', 'Access affected';
insert into ITIMAM.hr_events (select a.CNUM, a.FULL_NAME, a.EMAILID, a.DEPT, a.DIV, a.ISMANAGER, a.MGRCNUM, a.JOB_ROLE, a.SKILL_SET, current date, 0, 'Termination', 'PE records affected ', 'Access affected', substr(a.cnum, 7, 3) from ITIMAM.bp_previous a where not exists (select b.emailid from ITIMAM.bp_current b where a.emailid=b.emailid) and exists (select c.mail from itimuser.person_mail c where a.emailid=c.mail) and exists (select d.selfdn from itimuser.account d, itimuser.account_owner e, itimuser.person_mail c where d.dn=e.dn and e.owner=c.dn and a.emailid=c.mail));

-- 'Termination', 'PE records affected ', 'Access removed';
insert into ITIMAM.hr_events (select a.CNUM, a.FULL_NAME, a.EMAILID, a.DEPT, a.DIV, a.ISMANAGER, a.MGRCNUM, a.JOB_ROLE, a.SKILL_SET, current date, 0, 'Termination', 'PE records affected ', 'Access removed', substr(a.cnum, 7, 3) from ITIMAM.bp_previous a where not exists (select b.emailid from ITIMAM.bp_current b where a.emailid=b.emailid) and exists (select c.mail from itimuser.person_mail c where a.emailid=c.mail) and not exists (select d.selfdn from itimuser.account d, itimuser.account_owner e, itimuser.person_mail c where d.dn=e.dn and e.owner=c.dn and a.emailid=c.mail));


CALL SYSPROC.ADMIN_CMD ('RUNSTATS ON TABLE ITIMAM.hr_events ON ALL COLUMNS AND INDEXES ALL ALLOW WRITE ACCESS');
--CALL SYSPROC.ADMIN_CMD ('REORG INDEXES ALL FOR TABLE ITIMAM.hr_events ALLOW READ ACCESS');
--CALL SYSPROC.ADMIN_CMD ('REORG TABLE ITIMAM.hr_events ALLOW READ ACCESS');