import { GithubOutlined, LinkedinOutlined } from '@ant-design/icons';
import { DefaultFooter } from '@ant-design/pro-components';
import { useIntl } from 'umi';
import { BAOWEN_LINK } from '@/constants';

const Footer: React.FC = () => {
  const intl = useIntl();
  const defaultMessage = '宝文出品';
  // intl.formatMessage({
  //     id: 'app.copyright.produced',
  //     defaultMessage: '宝文出品',
  //   });

  const currentYear = new Date().getFullYear();

  return (
    <DefaultFooter
      copyright={`${currentYear} ${defaultMessage}`}
      links={[
        {
          key: 'baowen',
          title: 'Baowen',
          href: BAOWEN_LINK,
          blankTarget: true,
        },
        {
          key: 'github',
          title: (
            <>
              <GithubOutlined /> 宝文 GitHub
            </>
          ),
          href: 'https://github.com/PowerSixxx',
          blankTarget: true,
        },
        {
          key: 'linkedin',
          title: (
            <>
              <LinkedinOutlined /> 宝文 Linkedin
            </>
          ),
          href: 'https://www.linkedin.com/in/baowen-liu-959104340/',
          blankTarget: true,
        },
      ]}
    />
  );
};

export default Footer;
